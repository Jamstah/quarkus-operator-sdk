package io.quarkiverse.operatorsdk.test;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.fabric8.kubernetes.client.CustomResource;
import io.quarkiverse.operatorsdk.test.sources.*;
import io.quarkus.test.ProdBuildResults;
import io.quarkus.test.ProdModeTestResults;
import io.quarkus.test.QuarkusProdModeTest;

public class NoCRDGenerationTest {

    // Start unit test with your extension loaded
    @RegisterExtension
    static final QuarkusProdModeTest config = new QuarkusProdModeTest()
            .setApplicationName("test")
            .withApplicationRoot(
                    (jar) -> jar.addClasses(SimpleReconciler.class, SimpleCR.class, SimpleSpec.class, SimpleStatus.class))
            .overrideConfigKey("quarkus.operator-sdk.crd.generate", "false");

    @ProdBuildResults
    private ProdModeTestResults prodModeTestResults;

    @Test
    public void shouldCreateRolesAndRoleBindings() throws IOException {
        final var kubernetesDir = prodModeTestResults.getBuildDir().resolve("kubernetes");
        final var kubeManifest = kubernetesDir.resolve("kubernetes.yml");
        Assertions.assertTrue(Files.exists(kubeManifest));

        // checks that CRDs are NOT generated
        Assertions.assertFalse(Files.exists(kubernetesDir.resolve(CustomResource.getCRDName(TestCR.class) + "-v1.yml")));
        Assertions.assertFalse(Files.exists(kubernetesDir.resolve(CustomResource.getCRDName(SimpleCR.class) + "-v1.yml")));
    }
}

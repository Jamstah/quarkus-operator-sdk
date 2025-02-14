package io.quarkiverse.operatorsdk.bundle.sources;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import io.quarkiverse.operatorsdk.bundle.runtime.CSVMetadata;

@Group("halkyon.io")
@Version("v1alpha1")
@CSVMetadata(displayName = External.DISPLAY_NAME, description = External.DESCRIPTION)
public class External extends CustomResource<Void, Void> {

    public static final String DISPLAY_NAME = "External display name";
    public static final String DESCRIPTION = "External description";
}

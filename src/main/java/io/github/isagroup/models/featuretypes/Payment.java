package io.github.isagroup.models.featuretypes;

import java.util.Map;

import io.github.isagroup.models.Feature;
import io.github.isagroup.models.FeatureType;

public class Payment extends Feature {

    @Override
    public Map<String, Object> serializeFeature() {

        Map<String, Object> attributes = featureAttributesMap();
        attributes.put("type", FeatureType.PAYMENT.toString());
        return attributes;
    }

}

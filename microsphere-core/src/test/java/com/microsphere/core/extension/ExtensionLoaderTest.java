package com.microsphere.core.extension;

import com.microsphere.core.extension.support.ApplicationModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class ExtensionLoaderTest {

    @Test
    void load() {
        final ServiceProvider serviceProvider = ApplicationModel.get(ServiceProvider.class).load("role");
        Assertions.assertNotNull(serviceProvider);

        System.out.printf("name: %s, hashcode:%d", serviceProvider.getClass().getName(), serviceProvider.hashCode());
    }
}
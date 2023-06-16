package com.microsphere.faulttolerance.core.resilience4j;

import com.microsphere.faulttolerance.core.Bulkhead;
import com.microsphere.faulttolerance.core.ConfigConverter;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class Resilience4jBulkhead implements Bulkhead, ConfigConverter<Bulkhead.BulkheadOptions, BulkheadConfig> {

    private final io.github.resilience4j.bulkhead.Bulkhead delegate;

    public Resilience4jBulkhead(String name, BulkheadRegistry bulkheadRegistry, BulkheadOptions ops) {
        this.delegate = bulkheadRegistry.bulkhead(name, convert(ops));
    }

    @Override
    public <T> T execute(Supplier<T> supplier) {
        return this.delegate.executeSupplier(supplier);
    }

    @Override
    public BulkheadConfig convert(BulkheadOptions ops) {
        return BulkheadConfig.custom()
                .maxConcurrentCalls(ops.getConcurrentCalls())
                .build();
    }
}

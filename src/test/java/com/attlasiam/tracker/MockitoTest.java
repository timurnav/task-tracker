package com.attlasiam.tracker;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MockitoTest {

    @Test
    public void testSingleDefMultCall() {
        IntSupplier supplier = Mockito.mock(IntSupplier.class);
        Mockito.when(supplier.get()).thenReturn(100);

        Assertions.assertThat(supplier.get())
                .isEqualTo(100);


        Assertions.assertThat(supplier.get())
                .isEqualTo(100);
        Assertions.assertThat(supplier.get())
                .isEqualTo(100);
    }

    @Test
    public void testMultDef() {
        IntSupplier supplier = Mockito.mock(IntSupplier.class);
        Mockito.when(supplier.get())
                .thenReturn(100)
                .thenReturn(200)
                .thenThrow(new NoSuchElementException("msg"));

        Assertions.assertThat(supplier.get())
                .isEqualTo(100);
        Assertions.assertThat(supplier.get())
                .isEqualTo(200);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, supplier::get);
        Assertions.assertThat(thrown.getMessage())
                .isEqualTo("msg1");
    }

    private interface IntSupplier {
        int get();
    }
}

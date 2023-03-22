package com.rental.video.store.model;

import com.rental.video.store.entity.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataSupplierTest {
    @Mock
    private VideoRepository repository;
    @InjectMocks
    private DataSupplier dataSupplier;

    @Test
    void whenGetAllAdvertisersCalled_thenReturnValues() {
        List<String> expectedResult = new ArrayList<>(Arrays.asList("Matrix", "Matrix 2"));

        when(repository.findUniqueVideos()).thenReturn(expectedResult);

        assertThat(dataSupplier.getAllVideos()).isEqualTo(expectedResult);
    }
}

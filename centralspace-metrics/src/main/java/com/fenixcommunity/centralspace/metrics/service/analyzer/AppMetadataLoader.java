package com.fenixcommunity.centralspace.metrics.service.analyzer;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class AppMetadataLoader<T> {
    private final ClassGraph classGraph;

    public AppMetadataLoader() {
        classGraph = new ClassGraph().acceptPackages("com.fenixcommunity.centralspace")
                .enableClassInfo()
                .enableAnnotationInfo()
                .enableMethodInfo()
                .enableFieldInfo();
    }

    public MetadataInformation getMetadataInformation(final Class<T> className) {
        final ServiceLoader<T> loader = ServiceLoader.load(className);
        long serviceRepresentation = StreamSupport.stream(loader.spliterator(), false).count();

        return MetadataInformation.builder()
                .serviceRepresentation(serviceRepresentation)
                .build();
    }

    public String getMetadataInformationInJsonFormat() {
        try (final ScanResult result = classGraph.scan()) {
            return result.toJSON();
        }
    }

    public List<ClassInfoList> getClassGraphScanResultForAnnotation(final Class<T> annotationClass) {
        try (final ScanResult result = classGraph.scan()) {
            final String annotationClassName = annotationClass.getName();
            final ClassInfoList annotationForClasses = result.getClassesWithAnnotation(annotationClassName);
            final ClassInfoList annotationForMethods = result.getClassesWithMethodAnnotation(annotationClassName);
            final ClassInfoList annotationForFields = result.getClassesWithFieldAnnotation(annotationClassName);
            final ClassInfoList annotationForMethodsParam = result.getClassesWithMethodParameterAnnotation(annotationClassName);
            return Arrays.asList(annotationForClasses, annotationForMethods, annotationForFields, annotationForMethodsParam);
        }
    }

}

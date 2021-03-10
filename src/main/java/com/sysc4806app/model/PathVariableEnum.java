package com.sysc4806app.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @version milestone 1
 *
 * Indicates that an enum can be pulled from a request using the its url segment.
 * Must only be implemented by an enum.
 */
public interface PathVariableEnum {

    /**
     * Gets the url segment used to map from request to enum.
     * @return The url segment.
     */
    String getUrlSegment();

    /**
     * Maps a url segment to a specific enumeration of the provided enum type.
     * @param enumType The class of the type of enum onto which the url segment maps.
     * @param urlSegment The url segment which identifies a specific enumeration.
     * @param <T> The enum type that is a path variable enum.
     * @return The respective enumeration that matches the url segment.
     */
    static <T extends Enum<T> & PathVariableEnum> T urlSegmentToEnum(Class<T> enumType, String urlSegment) {
        Map<String, T> enumMap = Arrays.stream(enumType.getEnumConstants()).collect(Collectors.toMap(T::getUrlSegment, Function.identity()));
        return Optional.ofNullable(enumMap.get(urlSegment)).orElseThrow(() ->
                new IllegalArgumentException("Unknown urlSegment " + urlSegment + " for enum " + enumType.getName() + ". Allowed values are " + enumMap.keySet()));
    }
}

package agh.ii.prinjava.proj2;

import agh.ii.prinjava.proj2.dal.ImdbTop250;
import agh.ii.prinjava.proj2.model.Movie;
import agh.ii.prinjava.proj2.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

interface PlayWithMovies {

     /**
     * Returns the movies (only titles) directed (or co-directed) by a given director
     */
    static Set<String> ex01(String director) {
        return ImdbTop250.movies()
                .map(movies -> movies.stream()                                      // stream of all the movies in the database
                        .filter(movie -> movie.directors().contains(director))      // to have a stream filtered with only the specified directors
                        .map(Movie::title)                                          // the filtered stream is now mapped into movie titles
                        .collect(Collectors.toSet()))                               // it collects the title into a set, and the set will contain only unique movie titles
                .orElse(Set.of());                                                  // else it returns an empty set
    }

    /**
     * Returns the movies (only titles) in which an actor played
     */
    static Set<String> ex02(String actor) {
        return ImdbTop250.movies()
                .map(movies -> movies.stream()                                  // stream of all the movies in the database
                        .filter(movie -> movie.actors().contains(actor))        // to have a stream filtered with only the specified actors
                        .map(Movie::title)                                      // the filtered stream is now mapped into movie titles
                        .collect(Collectors.toSet()))                           // it collects the title into a set, and the set will contain only unique movie titles
                .orElse(Set.of());                                              // else it returns an empty set
    }

    /**
     * Returns the number of movies per director (as a map)
     */
    static Map<String, Long> ex03() {
        return ImdbTop250.movies()
                .map(movies -> movies.stream()                                                              // stream of all the movies in the database
                        .flatMap(movie -> movie.directors().stream())                                       // to have a stream of all the directors (may have occurrences)
                        .collect(Collectors.groupingBy(director -> director, Collectors.counting())))       // grouping by the directors to associate each director with the count of occurrences
                .orElse(Map.of());                                                                          // else it returns an empty map
    }

    /**
     * Returns the 10 directors with the most films on the list
     */
    static Map<String, Long> ex04() {
        throw new RuntimeException("ex04 is not implemented!");
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in {@link PlayWithMovies#ex04 ex04}
     */
    static Map<String, Set<String>> ex05() {
        throw new RuntimeException("ex05 is not implemented!");
    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {
        throw new RuntimeException("ex06 is not implemented!");
    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {
        throw new RuntimeException("ex07 is not implemented!");
    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from {@link PlayWithMovies#ex07 ex07}
     */
    static Map<String, Set<String>> ex08() {
        throw new RuntimeException("ex08 is not implemented!");
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often)
     */
    static Map<String, Long> ex09() {
        throw new RuntimeException("ex08 is not implemented!");
    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {
        throw new RuntimeException("ex10 is not implemented!");
    }
}


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
        // Get the list of movies from the data source
        List<Movie> movies = ImdbTop250.movies().orElse(Collections.emptyList());

        // Count the occurrences of each director and get the top 10 directors
        return movies.stream()
                .flatMap(movie -> movie.directors().stream())
                .collect(Collectors.groupingBy(director -> director, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in {@link PlayWithMovies#ex04 ex04}
     */
    static Map<String, Set<String>> ex05() {
        // Get the top 10 directors with the most films
        Map<String, Long> topDirectors = ex04();

        // Get the list of movies from the data source
        List<Movie> movies = ImdbTop250.movies().orElse(Collections.emptyList());

        // Map each director to a set of movie titles
        Map<String, Set<String>> result = new HashMap<>();

        topDirectors.keySet().forEach(director -> {
            // Filter movies by director and collect titles into a list
            List<String> moviesByDirector = movies.stream()
                    .filter(movie -> movie.directors().contains(director))
                    .map(Movie::title)
                    .collect(Collectors.toList());

            // Put the director and their movie titles into the result map
            result.put(director, new HashSet<>(moviesByDirector));
        });

        return result;
    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {
        // Get the list of movies from the data source
        List<Movie> movies = ImdbTop250.movies().orElse(Collections.emptyList());

        // Count the occurrences of each actor
        return movies.stream()
                .flatMap(movie -> movie.actors().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));
    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {
        return ImdbTop250.movies()
                .map(movies -> movies.stream()
                        .flatMap(movie -> movie.actors().stream())
                        .collect(Collectors.groupingBy(actor -> actor, Collectors.counting())))
                .orElse(Map.of())
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(9)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from {@link PlayWithMovies#ex07 ex07}
     */
    static Map<String, Set<String>> ex08() {
        Map<String, Long> topActors = ex07();

        return topActors.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> ImdbTop250.movies()
                                .map(movies -> movies.stream()
                                        .filter(movie -> movie.actors().contains(e.getKey()))
                                        .map(Movie::title)
                                        .collect(Collectors.toSet()))
                                .orElse(Set.of())
                ));
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often)
     */
    static Map<String, Long> ex09() {

        return ImdbTop250.movies()
                .map(movies -> movies.stream()
                        .flatMap(movie -> Utils.orderedPairsFrom(movie.actors()).stream())
                        .collect(Collectors.groupingBy(pair -> pair, Collectors.counting())))
                .orElse(Map.of())
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {

        Map<String, Long> topActorDuos = ex09();

        return topActorDuos.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> ImdbTop250.movies()
                                .map(movies -> movies.stream()
                                        .filter(movie -> Utils.orderedPairsFrom(movie.actors()).contains(e.getKey()))
                                        .map(Movie::title)
                                        .collect(Collectors.toSet()))
                                .orElse(Set.of())
                ));

    }
}

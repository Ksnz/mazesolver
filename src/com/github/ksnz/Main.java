package com.github.ksnz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    enum MazePart {
        WALL,
        PATH,
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            args = "-i test.maze -o solve.maze".split(" ");
        }
        Map<String, String> options = parseOptions(args);
        String filePath = options.get("-i");
        if (filePath == null) {
            filePath = options.get("--input");
        }
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Укажи имя файла в аргументах");
            return;
        }

        String fileOutputPath = options.get("-o");
        if (fileOutputPath == null) {
            filePath = options.get("--output");
        }
        if (fileOutputPath == null || fileOutputPath.isEmpty()) {
            System.out.println("Укажи имя файла вывода в аргументах");
            return;
        }

        initFile(filePath);

        final MazePart[][] mazeStructure = getMazeParts(filePath);

        ArrayList<Vector.Direction> normalizedPath = getShortestPath(mazeStructure);

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                Paths.get(fileOutputPath)))) {
            pw.println(normalizedPath.size());
            StringJoiner sj = new StringJoiner(" ");
            normalizedPath.forEach(direction -> sj.add(direction.getLabel()));
            pw.println(sj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Реализация алгоритма https://ru.wikipedia.org/wiki/Алгоритм_Ли
     * @param mazeStructure матрица лабиринта
     * @return массив направлений прохождения лабиринта или пустой массив, если путь не найден
     */
    private static ArrayList<Vector.Direction> getShortestPath(MazePart[][] mazeStructure) {
        int height = mazeStructure.length;
        int width = mazeStructure.length > 0 ? mazeStructure[0].length : 0;

        final int[][] weights = new int[width][height];

        Vector start = new Vector(0, 0); // TODO: 12.01.2019 Поддержка входа/выхода не только по углам
        Vector end = new Vector(width - 1, height - 1);

        Set<Vector> front = new HashSet<>();
        front.add(start);
        weights[start.x][start.y] = 1;
        do {
            Set<Vector> nextFront = new HashSet<>(8);
            front.forEach(vector -> {
                int currentWeight = weights[vector.x][vector.y];
                for (Vector.Direction direction : Vector.Direction.values()) {
                    Vector side = vector.getFromDirrection(direction);
                    if (side.isInBounds(weights) && weights[side.x][side.y] == 0 && mazeStructure[side.x][side.y] == MazePart.PATH) {
                        weights[side.x][side.y] = currentWeight + 1;
                        if (!side.equals(end)) {
                            nextFront.add(side);
                        }
                    }
                }
            });
            front.clear();
            front.addAll(nextFront);
        } while (!front.isEmpty());

        ArrayList<Vector.Direction> normalizedPath = new ArrayList<>();

        if (weights[end.x][end.y] == 0) {
            return normalizedPath; //пути нет
        }
        Vector current = end;
        int begin = weights[current.x][current.y];
        List<Vector.Direction> reversedPath = new ArrayList<>(begin);
        do {
            for (Vector.Direction direction : Vector.Direction.values()) {
                Vector side = current.getFromDirrection(direction);
                if (side.isInBounds(weights) && weights[side.x][side.y] > 0 && weights[side.x][side.y] + 1 == begin) {
                    reversedPath.add(direction);
                    begin--;
                    current = side;
                    break;
                }
            }
        } while (begin != 1);

        normalizedPath.ensureCapacity(reversedPath.size());

        for (int i = reversedPath.size() - 1; i >= 0; i--) {
            normalizedPath.add(reversedPath.get(i).getOpposite());
        }
        return normalizedPath;
    }

    private static MazePart[][] getMazeParts(String filePath) {
        final MazePart[][] mazeStructure;
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<String> lines = stream.collect(Collectors.toList());
            int height = lines.size();
            int width = lines.stream().mapToInt(String::length).max().orElse(0);
            mazeStructure = new MazePart[height][width];
            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                char[] line = lines.get(lineNumber).toCharArray();
                if (line.length < width) {
                    throw new RuntimeException("Line " + lineNumber + " shorter than maze width" + width);
                }
                for (int i = 0; i < width; i++) {
                    MazePart here;
                    switch (line[i]) {
                        case 'N':
                            here = MazePart.WALL;
                            break;
                        case 'Y':
                            here = MazePart.PATH;
                            break;
                        default:
                            throw new RuntimeException("Illegal char " + line[i] + " at line:" + lineNumber + " pos: " + i);
                    }
                    mazeStructure[lineNumber][i] = here;
                }
            }
            return mazeStructure;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка чтения файла");
        }
    }

    private static void initFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
                printWriter.println("YNNNNNYYYY");
                printWriter.println("YYYNNYYNNY");
                printWriter.println("YNYYYYNNYY");
                printWriter.println("NYYNNNNNYN");
                printWriter.println("YYNNNNYYYN");
                printWriter.println("NYYYNNYNNN");
                printWriter.println("YYNYNNYYYN");
                printWriter.println("YNNYYYYNYY");
                printWriter.println("YNNYNNNNNY");
                printWriter.println("YYYYNNNNNY");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Не смог создать файл");
            }
        }
    }

    static Map<String, String> parseOptions(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    options.put(arg, args[++i]);
                } else {
                    options.put(arg, "");
                }
            }
        }
        return options;
    }
}

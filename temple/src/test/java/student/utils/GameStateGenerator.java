package student.utils;

import game.GameState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GameStateGenerator {
    public static GameState generate(long seed) {
        GameState gameStateTest;
        var useGUI = false;
        var clazz = GameState.class;
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(long.class, boolean.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        constructor.setAccessible(true);
        try {
            gameStateTest = (GameState) constructor.newInstance(seed, useGUI);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return gameStateTest;
    }
}

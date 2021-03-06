/*
 * Copyright (c) 2015-2016 Me4502 (Matthew Miller)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.me4502.modularframework;

import org.spongepowered.api.Game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to call main functions from when used via Maven/Gradle shading.
 */
public class ShadedModularFramework {

    /**
     * Internal list of Module Controllers.
     */
    protected static List<ModuleController> controllerList = new ArrayList<>();

    /**
     * Register a new Module Controller.
     *
     * @param plugin The plugin object to register with.
     * @param game The game.
     * @return The newly registered ModuleController.
     */
    public static ModuleController registerModuleController(Object plugin, Game game) {
        //If a real copy is installed, use that over this one.
        try {
            Class clazz = Class.forName("com.me4502.modularframework.ModularFramework");
            return (ModuleController) clazz.getMethod("registerModuleController").invoke(null, plugin, game);
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ModuleController controller =  new ModuleController(plugin, game);
        controllerList.add(controller);
        return controller;
    }
}

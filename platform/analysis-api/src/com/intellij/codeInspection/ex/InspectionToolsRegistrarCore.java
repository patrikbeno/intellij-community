/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.codeInspection.ex;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;

public class InspectionToolsRegistrarCore {
  private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.ex.InspectionToolsRegistrarCore");
  static Object instantiateTool(@NotNull Class<?> toolClass) {
    try {
      return ReflectionUtil.newInstance(toolClass, ArrayUtil.EMPTY_CLASS_ARRAY);
    }
    catch (RuntimeException e) {
      LOG.error(e.getCause());
    }

    return null;
  }
}

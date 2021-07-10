/*
 * Copyright 2018-2021 Bingchuan Sun.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chuan.simple.bean.core.element.entity;

import java.lang.reflect.Parameter;

public class MethodParameterElement extends ParameterElement {

	
    public MethodParameterElement(String declaringClassName,
            String declaringExecutableName, String name) {
        super(declaringClassName, declaringExecutableName, name);
    }

    public MethodParameterElement(Parameter parameter) {
        super(parameter);
    }
    
}

/*
 *   Copyright 2024 Vonage
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vonage.client.auth;

import java.util.Map;

/**
 * Base class for auth methods that are appended to the request's query parameters.
 *
 * @since 8.8.0
 */
public abstract class QueryParamsAuthMethod extends AuthMethod {

    /**
     * Gets the auth parameters to be included in the query string.
     *
     * @param requestParams List of existing request parameters, which may be used in the computation.
     *
     * @return A new Map containing only the authentication parameters.
     */
    public abstract Map<String, String> getAuthParams(RequestQueryParams requestParams);
}

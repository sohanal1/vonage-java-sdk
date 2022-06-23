/*
 *   Copyright 2020 Vonage
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
package com.vonage.client.insight;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierDetails {
    private String networkCode;
    private String name;
    private String country;
    private NetworkType networkType;

    @JsonProperty("network_code")
    public String getNetworkCode() {
        return networkCode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("network_type")
    public NetworkType getNetworkType() {
        return networkType;
    }

    public enum NetworkType {
        MOBILE,
        LANDLINE,
        LANDLINE_PREMIUM,
        LANDLINE_TOLLFREE,
        VIRTUAL,
        UNKNOWN,
        PAGER;

        @JsonCreator
        public static NetworkType fromString(String name) {
            if (name == null || name.equalsIgnoreCase("null")) {
                return null;
            }
            try {
                return NetworkType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iax) {
                return UNKNOWN;
            }
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}

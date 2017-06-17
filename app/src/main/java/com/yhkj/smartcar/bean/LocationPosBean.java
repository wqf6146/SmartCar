package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class LocationPosBean {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * regeocode : {"formatted_address":"北京市朝阳区望京街道方恒国际中心B座方恒国际中心","addressComponent":{"country":"中国","province":"北京市","city":[],"citycode":"010","district":"朝阳区","adcode":"110105","township":"望京街道","towncode":"110105026000","neighborhood":{"name":"方恒国际中心","type":"商务住宅;楼宇;商住两用楼宇"},"building":{"name":"方恒国际中心B座","type":"商务住宅;楼宇;商务写字楼"},"streetNumber":{"street":"阜通东大街","number":"6-2号楼","location":"116.48129,39.9902869","direction":"西南","distance":"25.9205"}}}
     */

    private String status;
    private String info;
    private String infocode;
    private RegeocodeBean regeocode;

    public static LocationPosBean objectFromData(String str) {

        return new Gson().fromJson(str, LocationPosBean.class);
    }

    public static LocationPosBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), LocationPosBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<LocationPosBean> arrayLocationPosBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LocationPosBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<LocationPosBean> arrayLocationPosBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<LocationPosBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public RegeocodeBean getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(RegeocodeBean regeocode) {
        this.regeocode = regeocode;
    }

    public static class RegeocodeBean {
        /**
         * formatted_address : 北京市朝阳区望京街道方恒国际中心B座方恒国际中心
         * addressComponent : {"country":"中国","province":"北京市","city":[],"citycode":"010","district":"朝阳区","adcode":"110105","township":"望京街道","towncode":"110105026000","neighborhood":{"name":"方恒国际中心","type":"商务住宅;楼宇;商住两用楼宇"},"building":{"name":"方恒国际中心B座","type":"商务住宅;楼宇;商务写字楼"},"streetNumber":{"street":"阜通东大街","number":"6-2号楼","location":"116.48129,39.9902869","direction":"西南","distance":"25.9205"}}
         */

        private String formatted_address;
        private AddressComponentBean addressComponent;

        public static RegeocodeBean objectFromData(String str) {

            return new Gson().fromJson(str, RegeocodeBean.class);
        }

        public static RegeocodeBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), RegeocodeBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<RegeocodeBean> arrayRegeocodeBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<RegeocodeBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<RegeocodeBean> arrayRegeocodeBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<RegeocodeBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class AddressComponentBean {
            /**
             * country : 中国
             * province : 北京市
             * city : []
             * citycode : 010
             * district : 朝阳区
             * adcode : 110105
             * township : 望京街道
             * towncode : 110105026000
             * neighborhood : {"name":"方恒国际中心","type":"商务住宅;楼宇;商住两用楼宇"}
             * building : {"name":"方恒国际中心B座","type":"商务住宅;楼宇;商务写字楼"}
             * streetNumber : {"street":"阜通东大街","number":"6-2号楼","location":"116.48129,39.9902869","direction":"西南","distance":"25.9205"}
             */

            private String country;
            private String province;
            private String citycode;
            private String district;
            private String adcode;
            private String township;
            private String towncode;
            private NeighborhoodBean neighborhood;
            private BuildingBean building;
            private StreetNumberBean streetNumber;
            private List<?> city;

            public static AddressComponentBean objectFromData(String str) {

                return new Gson().fromJson(str, AddressComponentBean.class);
            }

            public static AddressComponentBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), AddressComponentBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<AddressComponentBean> arrayAddressComponentBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<AddressComponentBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<AddressComponentBean> arrayAddressComponentBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<AddressComponentBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getTownship() {
                return township;
            }

            public void setTownship(String township) {
                this.township = township;
            }

            public String getTowncode() {
                return towncode;
            }

            public void setTowncode(String towncode) {
                this.towncode = towncode;
            }

            public NeighborhoodBean getNeighborhood() {
                return neighborhood;
            }

            public void setNeighborhood(NeighborhoodBean neighborhood) {
                this.neighborhood = neighborhood;
            }

            public BuildingBean getBuilding() {
                return building;
            }

            public void setBuilding(BuildingBean building) {
                this.building = building;
            }

            public StreetNumberBean getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(StreetNumberBean streetNumber) {
                this.streetNumber = streetNumber;
            }

            public List<?> getCity() {
                return city;
            }

            public void setCity(List<?> city) {
                this.city = city;
            }

            public static class NeighborhoodBean {
                /**
                 * name : 方恒国际中心
                 * type : 商务住宅;楼宇;商住两用楼宇
                 */

                private String name;
                private String type;

                public static NeighborhoodBean objectFromData(String str) {

                    return new Gson().fromJson(str, NeighborhoodBean.class);
                }

                public static NeighborhoodBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), NeighborhoodBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<NeighborhoodBean> arrayNeighborhoodBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<NeighborhoodBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<NeighborhoodBean> arrayNeighborhoodBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<NeighborhoodBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class BuildingBean {
                /**
                 * name : 方恒国际中心B座
                 * type : 商务住宅;楼宇;商务写字楼
                 */

                private String name;
                private String type;

                public static BuildingBean objectFromData(String str) {

                    return new Gson().fromJson(str, BuildingBean.class);
                }

                public static BuildingBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), BuildingBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<BuildingBean> arrayBuildingBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<BuildingBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<BuildingBean> arrayBuildingBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<BuildingBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class StreetNumberBean {
                /**
                 * street : 阜通东大街
                 * number : 6-2号楼
                 * location : 116.48129,39.9902869
                 * direction : 西南
                 * distance : 25.9205
                 */

                private String street;
                private String number;
                private String location;
                private String direction;
                private String distance;

                public static StreetNumberBean objectFromData(String str) {

                    return new Gson().fromJson(str, StreetNumberBean.class);
                }

                public static StreetNumberBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), StreetNumberBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<StreetNumberBean> arrayStreetNumberBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<StreetNumberBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<StreetNumberBean> arrayStreetNumberBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<StreetNumberBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getDirection() {
                    return direction;
                }

                public void setDirection(String direction) {
                    this.direction = direction;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }
            }
        }
    }
}

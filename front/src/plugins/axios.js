/**
 * vue add axios
 */

"use strict";

import Vue from 'vue';
import axios from "axios";


// baseURL 설정
let config = {
    baseURL: 'http://localhost:8080/', // 원하는 서버의 주소로 변경해주세요
};

// timeout 설정
config.timeout = 60000; // 타임아웃 시간을 원하는 값으로 변경해주세요

// withCredentials 설정
config.withCredentials = true; // 필요에 따라 true 또는 false로 변경해주세요

// axios 인스턴스 생성
const axiosInstance = axios.create(config);

// 요청 인터셉터
axiosInstance.interceptors.request.use(
    function(config) {
        // 요청 전에 수행할 로직 추가
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// 응답 인터셉터
axiosInstance.interceptors.response.use(
    function(response) {
        // 응답 전에 수행할 로직 추가
        return response;
    },
    function(error) {
        return Promise.reject(error);
    }
);

Vue.use({
    install: function(Vue) {
        Vue.prototype.$axios = axiosInstance;
    }
});

export default {

}

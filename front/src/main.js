import Vue from 'vue'
import main from '@/main.vue'

import router from '@/plugins/router'
import vuetify from '@/plugins/vuetify'
import '@/plugins/axios'
import '@/plugins/moment'
import axios from 'axios';

Vue.prototype.$http = axios;
Vue.config.productionTip = false

new Vue({
    router,
    vuetify,
    render: h => h(main)
}).$mount('#app')

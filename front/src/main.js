import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './vuetify'
import "leaflet/dist/leaflet.css";
import "leaflet-geosearch/dist/geosearch.css";
import 'img-comparison-slider';

Vue.config.productionTip = false
Vue.config.ignoredElements = [/img-comparison-slider/];

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

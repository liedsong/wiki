<template>
  <a-layout-footer style="text-align: center">
    Song电子书<span v-if="user.id">，欢迎:{{user.name}}</span>
  </a-layout-footer>
</template>

<script lang="ts">
import {computed, defineComponent, onMounted} from 'vue';
import store from "@/store";
import {message, notification} from "ant-design-vue";
import {Tool} from "@/util/tool";

  export default defineComponent({
    name: 'the-footer',
    setup(){
      const user = computed(() => store.state.user);

      let websocket: any;
      //将要传给服务端的token
      let token: any;
      const onOpen = () => {
        console.log('WebSocket连接成功，状态码:', websocket.readyState)
      };
      const onMessage = (event: any) => {
        console.log('WebSocket收到消息:', event.data);
          notification['success']({
            message: '收到消息:',
            description: event.data,
          });
        };
      const onError = () => {
        console.log('WebSocket连接错误，状态码:', websocket.readyState)
      };
      const onClose = () => {
        console.log('WebSocket连接关闭，状态码:', websocket.readyState)
      };
      //websocket初始化
      const initWebSocket = () => {
        //连接成功
        websocket.onopen = onOpen;
        //收到消息的回调
        websocket.onmessage = onMessage;
        //连接错误
        websocket.onerror = onError;
        //连接关闭的回调
        websocket.onclose = onClose;
      }

      onMounted(() => {
        //WebSocket
        if('WebSocket' in window) {
          token = Tool.uuid(10);
          //连接地址：ws://127.0.0.1:8880/ws/xxx
          //H5自带的WebSocket
          websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/' + token);
          //为websocket绑定四个函数
          initWebSocket();

          //关闭
          // websocket.close();
        } else {
          alert('当前浏览器 不支持');
        }
      });
      return {
        user
      }
    }
  });
</script>
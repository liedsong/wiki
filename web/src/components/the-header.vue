<template>
  <a-layout-header class="header">
    <div class="logo1">wiki知识库</div>
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="1">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="2" v-if="user.id">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="3" v-if="user.id">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="4" v-if="user.id">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>
      <a-menu-item key="5">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
      <a class="login-menu" style="order: 100" v-if="user.id">
        <span>您好：{{ user.name }}</span>
      </a>
      <a class="login-menu" style="order: 100" v-if="!user.id" @click="showLoginModal">
        <span>登录</span>
      </a>
      <a-popconfirm
          title="是否确认退出?"
          ok-text="是"
          cancel-text="否"
          @confirm="logout"
      >
        <a class="logout" style="order: 100" v-if="user.id">
          <span>退出登录</span>
        </a>
      </a-popconfirm>
    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.loginName"/>
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password"/>
        </a-form-item>
      </a-form>
    </a-modal>

  </a-layout-header>
</template>

<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import { message } from "ant-design-vue";
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;


export default defineComponent({
    name: 'the-header',
    setup() {
      //登录后保存的
      const user = computed(() => store.state.user);

      //登录
      const loginUser = ref({
        loginName: "test",
        password: "test"
      });
      const loginModalVisible = ref(false);
      const loginModalLoading = ref(false);
      const showLoginModal = () => {
        loginModalVisible.value = true;
      };

      //开始登录
      const login = () => {
        console.log("开始登录");
        loginModalLoading.value = true;
        loginUser.value.password = hexMd5(loginUser.value.password + KEY);
        axios.post('/user/login', loginUser.value).then((response) => {
          loginModalLoading.value = false;
          const data = response.data;
          if (data.success) {
            loginModalVisible.value = false;
            message.success("登录成功！");

            store.commit("setUser", data.content);
          } else {
            message.error(data.message);
          }
        });
      };

      //退出登录
      const logout = () => {
        console.log("开始退出登录");
        axios.get('/user/logout/' + user.value.token).then((response) => {
          loginModalLoading.value = false;
          const data = response.data;
          if (data.success) {
            message.success("退出成功！");
            store.commit("setUser", {});
          } else {
            message.error(data.message);
          }
        });
      };

      return {
        loginModalVisible,
        loginModalLoading,
        showLoginModal,
        login,
        loginUser,
        user,
        logout,
      }
    }
  });
</script>

<style>
.login-menu{
  float: right;
  color: white;
  margin-left: auto;
  /*font-size: large;*/
}
.logout{
  float: right;
  color: bisque;
  margin-left: 10px;
  font-size: small;
}

.logo1 {
  width: 120px;
  height: 31px;
  /*background: rgba(255, 255, 255, 0.2);*/
  /*margin: 16px 28px 16px 0;*/
  float: left;
  color: white;
  font-size: 18px;
}

</style>
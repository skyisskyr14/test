import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({ token: '', user: null }),
  actions: {
    setLogin(data) {
      this.token = data.token
      this.user = data.user
    }
  }
})

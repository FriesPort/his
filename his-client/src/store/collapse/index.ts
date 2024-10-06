import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useCollapseStore = defineStore('collapse', () => {
  const iscollapse = ref(false)
  const getCollapse = computed(() => {
    return iscollapse.value
  })
  function setCollapse() {
    iscollapse.value = !iscollapse.value
  }
  return { iscollapse, getCollapse, setCollapse }
})


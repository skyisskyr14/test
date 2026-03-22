<template>
  <el-card>
    <template #header>
      <div class="between">
        <span>考核方案管理</span>
        <el-button type="primary" @click="dialog=true">新建方案</el-button>
      </div>
    </template>
    <el-table :data="plans" border>
      <el-table-column prop="planCode" label="方案编码"/>
      <el-table-column prop="planName" label="方案名称"/>
      <el-table-column prop="cycleType" label="周期"/>
      <el-table-column prop="status" label="状态"/>
    </el-table>
  </el-card>

  <el-dialog v-model="dialog" title="新建考核方案">
    <el-form :model="form" label-width="90px">
      <el-form-item label="方案编码"><el-input v-model="form.planCode"/></el-form-item>
      <el-form-item label="方案名称"><el-input v-model="form.planName"/></el-form-item>
      <el-form-item label="周期类型"><el-input v-model.number="form.cycleType" placeholder="1月/2季/3年"/></el-form-item>
      <el-form-item label="开始日期"><el-date-picker v-model="form.startDate" value-format="YYYY-MM-DD"/></el-form-item>
      <el-form-item label="结束日期"><el-date-picker v-model="form.endDate" value-format="YYYY-MM-DD"/></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialog=false">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createPlanApi, planListApi } from '../api/modules'

const plans = ref([])
const dialog = ref(false)
const form = reactive({ planCode: '', planName: '', cycleType: 2, startDate: '', endDate: '', status: 0 })

const load = async () => {
  const { data } = await planListApi()
  plans.value = data.data
}

const save = async () => {
  await createPlanApi(form)
  ElMessage.success('创建成功')
  dialog.value = false
  Object.assign(form, { planCode: '', planName: '', cycleType: 2, startDate: '', endDate: '', status: 0 })
  load()
}

onMounted(load)
</script>

<style scoped>
.between { display:flex; justify-content:space-between; align-items:center; }
</style>

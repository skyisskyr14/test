<template>
  <el-card>
    <template #header>
      <div class="between"><span>指标库管理</span><el-button type="primary" @click="dialog=true">新增指标</el-button></div>
    </template>
    <el-table :data="list" border>
      <el-table-column prop="indicatorName" label="指标名称"/>
      <el-table-column prop="indicatorType" label="类型"/>
      <el-table-column prop="weight" label="权重"/>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="dialog" title="新增指标">
    <el-form :model="form" label-width="90">
      <el-form-item label="名称"><el-input v-model="form.indicatorName"/></el-form-item>
      <el-form-item label="类型"><el-input-number v-model="form.indicatorType" :min="1" :max="2"/></el-form-item>
      <el-form-item label="权重"><el-input-number v-model="form.weight" :min="1" :max="100"/></el-form-item>
      <el-form-item label="说明"><el-input type="textarea" v-model="form.description"/></el-form-item>
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
import { createIndicatorApi, deleteIndicatorApi, indicatorListApi } from '../api/modules'

const list = ref([])
const dialog = ref(false)
const form = reactive({ indicatorName: '', indicatorType: 1, weight: 30, description: '' })

const load = async () => {
  const { data } = await indicatorListApi()
  list.value = data.data
}

const save = async () => {
  await createIndicatorApi(form)
  ElMessage.success('新增成功')
  dialog.value = false
  Object.assign(form, { indicatorName: '', indicatorType: 1, weight: 30, description: '' })
  load()
}

const remove = async (id) => {
  await deleteIndicatorApi(id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped>.between{display:flex;justify-content:space-between;align-items:center}</style>

<template>
  <el-card>
    <template #header>
      <div class="between">
        <span>指标库管理</span>
        <div class="actions">
          <el-input v-model="keyword" placeholder="搜索指标名称" clearable style="width: 220px" />
          <el-select v-model="typeFilter" placeholder="类型筛选" clearable style="width: 120px">
            <el-option label="定量KPI" :value="1" />
            <el-option label="定性评价" :value="2" />
          </el-select>
          <el-button type="primary" @click="dialog=true">新增指标</el-button>
        </div>
      </div>
    </template>
    <el-table :data="filteredList" border v-loading="loading">
      <el-table-column prop="indicatorName" label="指标名称" min-width="180"/>
      <el-table-column prop="indicatorType" label="类型" width="120">
        <template #default="{ row }">{{ row.indicatorType === 1 ? '定量KPI' : '定性评价' }}</template>
      </el-table-column>
      <el-table-column prop="weight" label="权重" width="180">
        <template #default="{ row }">
          <el-progress :percentage="Number(row.weight)" :show-text="true" :stroke-width="10" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="dialog" title="新增指标">
    <el-form :model="form" :rules="rules" ref="formRef" label-width="90">
      <el-form-item label="名称" prop="indicatorName"><el-input v-model="form.indicatorName"/></el-form-item>
      <el-form-item label="类型" prop="indicatorType"><el-input-number v-model="form.indicatorType" :min="1" :max="2"/></el-form-item>
      <el-form-item label="权重" prop="weight"><el-input-number v-model="form.weight" :min="1" :max="100"/></el-form-item>
      <el-form-item label="说明"><el-input type="textarea" v-model="form.description"/></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialog=false">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createIndicatorApi, deleteIndicatorApi, indicatorListApi } from '../api/modules'

const list = ref([])
const loading = ref(false)
const dialog = ref(false)
const keyword = ref('')
const typeFilter = ref(null)
const formRef = ref(null)
const form = reactive({ indicatorName: '', indicatorType: 1, weight: 30, description: '' })
const rules = {
  indicatorName: [{ required: true, message: '请输入指标名称', trigger: 'blur' }],
  indicatorType: [{ required: true, message: '请选择类型', trigger: 'change' }],
  weight: [{ required: true, message: '请输入权重', trigger: 'change' }]
}

const filteredList = computed(() => list.value.filter(item => {
  const hitKeyword = !keyword.value || item.indicatorName?.includes(keyword.value)
  const hitType = !typeFilter.value || item.indicatorType === typeFilter.value
  return hitKeyword && hitType
}))

const load = async () => {
  loading.value = true
  const { data } = await indicatorListApi()
  list.value = data.data || []
  loading.value = false
}

const save = async () => {
  await formRef.value.validate()
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

<style scoped>
.between {display:flex;justify-content:space-between;align-items:center;gap:12px}
.actions {display:flex;align-items:center;gap:10px}
</style>

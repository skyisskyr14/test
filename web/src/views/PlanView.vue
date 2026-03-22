<template>
  <el-card>
    <template #header>
      <div class="between">
        <span>考核方案管理</span>
        <div class="header-actions">
          <el-input v-model="keyword" placeholder="搜索方案名称/编码" clearable style="width: 240px" />
          <el-select v-model="statusFilter" placeholder="状态筛选" clearable style="width: 130px">
            <el-option label="草稿" :value="1" />
            <el-option label="待审批" :value="2" />
            <el-option label="已发布" :value="3" />
            <el-option label="已结束" :value="4" />
          </el-select>
          <el-button type="primary" @click="dialog=true">新建方案</el-button>
        </div>
      </div>
    </template>
    <el-table :data="filteredPlans" border v-loading="loading">
      <el-table-column prop="id" label="ID" width="70"/>
      <el-table-column prop="planCode" label="方案编码"/>
      <el-table-column prop="planName" label="方案名称"/>
      <el-table-column prop="cycleType" label="周期">
        <template #default="{ row }">{{ cycleTypeText(row.cycleType) }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="340">
        <template #default="scope">
          <el-button size="small" @click="publish(scope.row)">发布</el-button>
          <el-button size="small" type="warning" @click="openAssignIndicator(scope.row)">分配指标</el-button>
          <el-button size="small" type="success" @click="openAssignUser(scope.row)">分配人员</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="dialog" title="新建考核方案">
    <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
      <el-form-item label="方案编码" prop="planCode"><el-input v-model="form.planCode"/></el-form-item>
      <el-form-item label="方案名称" prop="planName"><el-input v-model="form.planName"/></el-form-item>
      <el-form-item label="周期类型" prop="cycleType">
        <el-select v-model="form.cycleType">
          <el-option label="月度" :value="1"/>
          <el-option label="季度" :value="2"/>
          <el-option label="年度" :value="3"/>
        </el-select>
      </el-form-item>
      <el-form-item label="开始日期" prop="startDate"><el-date-picker v-model="form.startDate" value-format="YYYY-MM-DD"/></el-form-item>
      <el-form-item label="结束日期" prop="endDate"><el-date-picker v-model="form.endDate" value-format="YYYY-MM-DD"/></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialog=false">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="indicatorDialog" title="分配方案指标">
    <el-checkbox-group v-model="selectedIndicators">
      <el-checkbox v-for="item in indicators" :key="item.id" :label="item.id">{{ item.indicatorName }}（{{ item.weight }}%）</el-checkbox>
    </el-checkbox-group>
    <template #footer>
      <el-button @click="indicatorDialog=false">取消</el-button>
      <el-button type="primary" @click="submitAssignIndicator">确定</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="userDialog" title="分配参评人员">
    <el-form :model="participantForm" label-width="90px">
      <el-form-item label="员工ID"><el-input-number v-model="participantForm.userId" :min="1"/></el-form-item>
      <el-form-item label="评分人ID"><el-input-number v-model="participantForm.assessorId" :min="1"/></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="userDialog=false">取消</el-button>
      <el-button type="primary" @click="submitAssignUser">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  assignPlanIndicatorsApi,
  assignPlanParticipantsApi,
  createPlanApi,
  indicatorListApi,
  planListApi,
  publishPlanApi
} from '../api/modules'

const plans = ref([])
const loading = ref(false)
const dialog = ref(false)
const indicatorDialog = ref(false)
const userDialog = ref(false)
const indicators = ref([])
const selectedIndicators = ref([])
const currentPlanId = ref(null)
const keyword = ref('')
const statusFilter = ref(null)
const formRef = ref(null)
const form = reactive({ planCode: '', planName: '', cycleType: 2, startDate: '', endDate: '', status: 0 })
const participantForm = reactive({ userId: 3, assessorId: 2 })

const rules = {
  planCode: [{ required: true, message: '请输入方案编码', trigger: 'blur' }],
  planName: [{ required: true, message: '请输入方案名称', trigger: 'blur' }],
  cycleType: [{ required: true, message: '请选择周期类型', trigger: 'change' }],
  startDate: [{ required: true, message: '请选择开始日期', trigger: 'change' }],
  endDate: [{ required: true, message: '请选择结束日期', trigger: 'change' }]
}

const filteredPlans = computed(() => plans.value.filter(item => {
  const hitKeyword = !keyword.value || item.planName?.includes(keyword.value) || item.planCode?.includes(keyword.value)
  const hitStatus = !statusFilter.value || item.status === statusFilter.value
  return hitKeyword && hitStatus
}))

const cycleTypeText = (v) => ({ 1: '月度', 2: '季度', 3: '年度' }[v] || `类型${v}`)
const statusText = (v) => ({ 0: '草稿', 1: '待审批', 2: '已发布', 3: '已结束' }[v] || `状态${v}`)
const statusTagType = (v) => ({ 0: 'info', 1: 'warning', 2: 'success', 3: '' }[v] || 'info')

const load = async () => {
  loading.value = true
  const { data } = await planListApi()
  plans.value = data.data || []
  loading.value = false
}

const save = async () => {
  await formRef.value.validate()
  await createPlanApi(form)
  ElMessage.success('创建成功')
  dialog.value = false
  Object.assign(form, { planCode: '', planName: '', cycleType: 2, startDate: '', endDate: '', status: 0 })
  load()
}

const publish = async (row) => {
  await publishPlanApi(row.id)
  ElMessage.success('方案已发布')
  load()
}

const openAssignIndicator = async (row) => {
  currentPlanId.value = row.id
  const { data } = await indicatorListApi()
  indicators.value = data.data || []
  selectedIndicators.value = indicators.value.map(i => i.id)
  indicatorDialog.value = true
}

const submitAssignIndicator = async () => {
  const selected = indicators.value.filter(i => selectedIndicators.value.includes(i.id))
  const payload = selected.map(i => ({ indicatorId: i.id, assignedWeight: i.weight }))
  await assignPlanIndicatorsApi(currentPlanId.value, payload)
  ElMessage.success('指标分配完成')
  indicatorDialog.value = false
}

const openAssignUser = (row) => {
  currentPlanId.value = row.id
  userDialog.value = true
}

const submitAssignUser = async () => {
  await assignPlanParticipantsApi(currentPlanId.value, [participantForm])
  ElMessage.success('参与人分配完成并生成任务')
  userDialog.value = false
}

onMounted(load)
</script>

<style scoped>
.between { display:flex; justify-content:space-between; align-items:center; gap: 12px; }
.header-actions { display: flex; align-items: center; gap: 10px; }
</style>

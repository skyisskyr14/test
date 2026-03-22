<template>
  <el-card>
    <template #header>
      <div class="between">
        <span>考核方案管理</span>
        <el-button type="primary" @click="dialog=true">新建方案</el-button>
      </div>
    </template>
    <el-table :data="plans" border>
      <el-table-column prop="id" label="ID" width="70"/>
      <el-table-column prop="planCode" label="方案编码"/>
      <el-table-column prop="planName" label="方案名称"/>
      <el-table-column prop="cycleType" label="周期"/>
      <el-table-column prop="status" label="状态"/>
      <el-table-column label="操作" width="320">
        <template #default="scope">
          <el-button size="small" @click="publish(scope.row)">发布</el-button>
          <el-button size="small" type="warning" @click="openAssignIndicator(scope.row)">分配指标</el-button>
          <el-button size="small" type="success" @click="openAssignUser(scope.row)">分配人员</el-button>
        </template>
      </el-table-column>
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
import { onMounted, reactive, ref } from 'vue'
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
const dialog = ref(false)
const indicatorDialog = ref(false)
const userDialog = ref(false)
const indicators = ref([])
const selectedIndicators = ref([])
const currentPlanId = ref(null)
const form = reactive({ planCode: '', planName: '', cycleType: 2, startDate: '', endDate: '', status: 0 })
const participantForm = reactive({ userId: 3, assessorId: 2 })

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

const publish = async (row) => {
  await publishPlanApi(row.id)
  ElMessage.success('方案已发布')
  load()
}

const openAssignIndicator = async (row) => {
  currentPlanId.value = row.id
  const { data } = await indicatorListApi()
  indicators.value = data.data
  selectedIndicators.value = data.data.map(i => i.id)
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
.between { display:flex; justify-content:space-between; align-items:center; }
</style>

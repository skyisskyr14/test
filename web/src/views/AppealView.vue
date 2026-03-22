<template>
  <el-row :gutter="16">
    <el-col :span="10">
      <el-card>
        <template #header>提交绩效申诉</template>
        <el-form :model="form" label-width="90px">
          <el-form-item label="结果ID"><el-input-number v-model="form.resultId" :min="1"/></el-form-item>
          <el-form-item label="申诉内容"><el-input type="textarea" v-model="form.appealContent"/></el-form-item>
          <el-button type="primary" @click="submit">提交申诉</el-button>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="14">
      <el-card>
        <template #header>申诉处理列表</template>
        <el-table :data="list" border>
          <el-table-column prop="id" label="ID" width="60"/>
          <el-table-column prop="appealContent" label="申诉内容"/>
          <el-table-column prop="appealStatus" label="状态" width="90"/>
          <el-table-column label="处理">
            <template #default="scope">
              <el-button size="small" type="success" @click="process(scope.row,3)">采纳</el-button>
              <el-button size="small" type="danger" @click="process(scope.row,2)">驳回</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { appealListApi, createAppealApi, processAppealApi } from '../api/modules'

const list = ref([])
const form = reactive({ resultId: 1, appealContent: '' })

const load = async () => {
  const { data } = await appealListApi()
  list.value = data.data
}

const submit = async () => {
  await createAppealApi(form)
  ElMessage.success('提交成功')
  form.appealContent = ''
  load()
}

const process = async (row, status) => {
  await processAppealApi(row.id, { appealStatus: status, processComment: status === 3 ? '同意调整' : '维持原结果' })
  ElMessage.success('处理完成')
  load()
}

onMounted(load)
</script>

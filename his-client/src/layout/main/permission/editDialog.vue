<template>
  <a-button type="primary" @click="showModal">
    <template #icon>
      <plus-outlined />
    </template>
    编辑
  </a-button>
  <a-modal v-model:visible="visible" title="权限编辑" @ok="handleOk">
    <template #footer>
      <a-button key="back" @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" @click="handleOk">确定</a-button>
    </template>
    <a-form>
      <a-form-item label="父级菜单：" style="width: 200px;">
        <a-cascader v-model:value="faterValue" :options="options" placeholder="无" change-on-select />
      </a-form-item>
      <a-form-item label="权限名称：" style="width: 200px;">
        <a-input v-model:value="props.record.name" placeholder="请输入权限名称">
        </a-input>
      </a-form-item>
      <a-form-item label="权限描述：" style="width: 200px;">
        <a-input v-model:value="props.record.detail" type="passward" placeholder="请输入权限描述">
        </a-input>
      </a-form-item>
      <a-form-item label="可用状态：" style="width: 200px;">
        <a-radio-group v-model:value="props.record.is_use">
          <a-radio :value="1">是</a-radio>
          <a-radio :value="0">否</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { addUserApi } from '@/api/user';
import { message } from 'ant-design-vue';
import type { CascaderProps } from 'ant-design-vue';
import { permisionEdit } from '@/api/permission/index';
const options: CascaderProps['options'] = [
  {
    value: '0',
    label: '无',
  },
  {
    value: '1',
    label: '床位管理',
    // children: [
    //   {
    //     value: 'hangzhou',
    //     label: 'Hangzhou',
    //   },
    // ],
  },
  {
    value: '2',
    label: '患者管理',
  },
  {
    value: '3',
    label: '床位分配',
  },
];
const faterValue = ref<string[]>(['0']);


const props = defineProps<{
  record: any
}>()
console.log(props.record);


const visible = ref<boolean>(false);

const detail = ref('')
const name = ref('')
const is_datarule = ref<number>(1)
const is_use = ref<number>(1)
const showModal = () => {
  visible.value = true;
};

const handleOk = async () => {
  visible.value = false;
  let params = {
    permissionId: props.record.id,
    permission: {
      name: props.record.name,
      description: props.record.detail
    }

  }
  //   name.value = ''
  //   status.value = 1
  //   username.value = ''
  //   passward.value = ''
  //   usernumber.value = ''
  //   phone.value = ''
  //   console.log('新增', params)
  //   await addUserApi(params)
  //   message.success('用户新增成功')
  //   // props.getUserList()
  let result = await permisionEdit(params)
  message.success('操作成功')
};

const handleCancel = () => {
  visible.value = false;
};

</script>

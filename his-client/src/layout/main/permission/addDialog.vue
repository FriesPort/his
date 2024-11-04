<template>
  <div>
    <a-button type="primary" @click="showModal">
      <template #icon>
        <plus-outlined />
      </template>
      权限注册
    </a-button>
    <a-modal v-model:visible="visible" title="权限注册" @ok="handleOk">
      <template #footer>
        <a-button key="back" @click="handleCancel">取消</a-button>
        <a-button key="submit" type="primary" @click="handleOk">新增</a-button>
      </template>
      <a-form>
        <!-- <a-form-item label="父级菜单：" style="width: 200px;">
          <a-cascader v-model:value="faterValue" :options="options" placeholder="无" change-on-select />
        </a-form-item> -->
        <a-form-item label="权限名称：" style="width: 200px;">
          <a-input v-model:value="name" placeholder="请输入权限名称">
          </a-input>
        </a-form-item>
        <!-- <a-form-item label="权限描述：" style="width: 200px;">
          <a-input v-model:value="detail" type="text" placeholder="请输入权限描述">
          </a-input>
        </a-form-item> -->
        <a-form-item label="可用状态：" style="width: 200px;">
          <a-radio-group v-model:value="is_use">
            <a-radio :value="1">是</a-radio>
            <a-radio :value="0">否</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="数据规则：" style="width: 200px;">
          <a-radio-group v-model:value="rule_flag">
            <a-radio :value="1">开</a-radio>
            <a-radio :value="0">关</a-radio>
          </a-radio-group>
        </a-form-item>
        <div v-if="flag">
          <a-form-item label="规则名字：" style="width: 200px;">
            <a-input v-model:value="ruleName" type="text" placeholder="请输入数据规则名字">
            </a-input>
          </a-form-item>
          <a-form-item label="规则列" style="width: 200px;">
            <a-input v-model:value="ruleColumn" placeholder="请输入规则列">
            </a-input>
            <!-- <a-select placeholder="请选择">
              <a-select-option v-for="item in ruleColumn" :key="item.id" value="demo">{{ item.name }}</a-select-option>
            </a-select> -->
          </a-form-item>
          <a-form-item label="规则条件：" style="width: 200px;">
            <a-input v-model:value="ruleConditions" placeholder="请输入规则条件">
            </a-input>
          </a-form-item>
          <a-form-item label="规则值：" style="width: 200px;">
            <a-input v-model:value="ruleValue" placeholder="请输入规则值">
            </a-input>
          </a-form-item>
        </div>
      </a-form>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { addUserApi } from '@/api/user';
import { message } from 'ant-design-vue';
import type { CascaderProps } from 'ant-design-vue';
import { permisionRegister } from '@/api/permission/index';
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

// const props = defineProps<{
//   getUserList: Function
// }>()
const visible = ref<boolean>(false);

const detail = ref('')
const name = ref('')
const is_datarule = ref<number>(1)
const is_use = ref<number>(1)
const rule_flag = ref<number>(0)
const showModal = () => {
  visible.value = true;
};


let rulesEdit = ref(false)
const ruleName = ref('')
const ruleConditions = ref('')
const ruleColumn = ref('')
const ruleValue = ref('')

const handleOk = async () => {
  visible.value = false;
  let params = {
    name: name.value,
    description: detail.value,
    isDatarule: rule_flag.value,
    ruleName: ruleName.value,
    ruleColumn: ruleColumn.value,
    ruleConditions: ruleConditions.value,
    ruleValue: ruleValue.value

  }
  //   name.value = ''
  //   status.value = 1
  //   username.value = ''
  //   passward.value = ''
  //   usernumber.value = ''
  //   phone.value = ''
  //   console.log('新增', params)
  await permisionRegister(params)
  //   message.success('用户新增成功')
  //   // props.getUserList()
};

const handleCancel = () => {
  visible.value = false;
};

const flag = ref(false)
//数据规则编辑
watch(() => rule_flag.value, (newValue: Number) => {
  if (newValue === 1) {
    flag.value = true
  } else {
    flag.value = false
  }
})
</script>

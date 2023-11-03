<template>
  <div>
    <v-data-table
        :headers='headers'
        :items='items'
        :serverItemsLength='totalItems'
        :options.sync='options'
        :loading='isLoading'
        loadingText='data loading'
        noDataText='Null'

        :footerProps='{
                showFirstLastPage: true,
                pageText: `${options.page}/${totalPages} 페이지`,
                itemsPerPageText: `페이지당 행 개수`,
                itemsPerPageOptions: [5, 10, 20],
            }'
    >
      <template v-slot:top>
        <div class='text-h6 font-weight-bold pl-8 pt-6'>
          목록 ({{ totalItems }})
          <v-btn icon @click='toggleIsOpen'>
            <v-icon>
              mdi-plus-circle
            </v-icon>
          </v-btn>
        </div>
      </template>
      <template v-slot:item.actions='{ item }'>
        <v-btn @click='handleEditClick(item)' icon class='mr-1'>
          <v-icon color='blue'>
            mdi-pencil
          </v-icon>
        </v-btn>
        <v-btn @click='handleRemoveClick(item)' icon>
          <v-icon color='red'>
            mdi-delete-sweep
          </v-icon>
        </v-btn>
      </template>
    </v-data-table>

    <v-dialog :value='isOpen' @click:outside='toggleIsOpen' max-width='768px'>
      <v-card class='pa-4'>
        <v-card-title class='text-h6 font-weight-bold pb-8'>
          {{ !activeId ? '등록' : '수정' }}하기
        </v-card-title>
        <v-card-text class='pb-2'>
          <v-form ref='form' lazy-validation>
            <v-text-field
                v-model='inputValues.title'
                label='제목'
                placeholder='제목을 입력하세요.'
                :rules='requiredRules'
                required
                clearable
                outlined
            />
            <v-text-field
                v-model='inputValues.name'
                label='이름'
                placeholder='이름을 입력하세요.'
                :rules='requiredRules'
                required
                clearable
                outlined
            />
            <v-text-field
                v-model='inputValues.manual'
                label='설명'
                placeholder='설명을 입력하세요.'
                :rules='requiredRules'
                required
                clearable
                outlined
            />
            <v-text-field
                v-model='inputValues.content'
                label='내용'
                placeholder='내용을 입력하세요.'
                :rules='requiredRules'
                required
                clearable
                outlined
            />
          </v-form>
          <div class='d-flex mt-0' style='justify-content: end; gap: 12px;'>
            <v-btn @click='toggleIsOpen' outlined>
              <v-icon left>
                mdi-close-circle-outline
              </v-icon>
              닫기
            </v-btn>
            <v-btn @click='handleSaveClick' outlined>
              <v-icon left>
                mdi-content-save-outline
              </v-icon>
              저장
            </v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import Resource from '@/api/server.js'

export default {
  data: () => ({
    headers: [
      { text: '아이디', value: 'id', align: 'center', width: '10%' },
      { text: '이름', value: 'name', align: 'center' },
      { text: '제목', value: 'title', align: 'center' },
      { text: '설명', value: 'manual', align: 'center'},
      { text: '내용', value: 'content', align: 'center'},
      { text: '수정 / 삭제', value: 'actions', align: 'center', sortable: false },
    ],
    items: [],
    totalItems: 0,
    totalPages: 0,
    isLoading: true,

    isOpen: false,
    activeId: null,
    inputValues: { title: '', name: '', manual: '', content: '' },
    requiredRules: [
      v => !!v || '필수 입력사항입니다.',
    ],

    options: {},
  }),
  watch: {
    isOpen(curr) {
      // this.$refs.form?.resetValidation()
      if (!curr) {
        this.activeId = null
        this.inputValues = { title: '', name: '', manual: '', content: '' }
      }
    },
    options: {
      handler() {
        this.searchApi(this.options)
      },
      deep: true,
      immediate: true,
    },
  },
  methods: {
    toggleIsOpen() {
      this.isOpen = !this.isOpen
    },

    // Event Handlers
    handleEditClick(item) {
      this.readApi(item.id)
    },
    handleSaveClick() {
      const isValidate = this.$refs.form.validate()
      if (!isValidate) return

      if (!this.activeId) {
        // 등록
        const isConfirm = confirm('등록하시겠습니까?')
        if (!isConfirm) return

        this.createApi()
      } else {
        // 수정
        const isConfirm = confirm('수정하시겠습니까?')
        if (!isConfirm) return

        this.updateApi(this.activeId)
      }
    },
    handleRemoveClick(item) {
      const isConfirm = confirm('[ ' + item.title + ' ] (을)를 정말로 삭제하시겠습니까?')
      if (!isConfirm) return

      this.deleteApi(item.id)
    },

    // APIs
    searchApi(options) {
      this.isLoading = true
      if (options) this.options = options
      const { page, itemsPerPage, sortBy, sortDesc } = this.options
      Resource.post
          .search({
            params: {
              size: itemsPerPage,
              page: page - 1 || undefined,
              sort: sortBy?.length ? `${sortBy[0]},${sortDesc[0] ? 'DESC' : 'ASC'}` : undefined,
            },
          })
          .then((res) => {
            this.items = res.data._embedded.post
            this.totalItems = res.data.page.totalElements
            this.totalPages = res.data.page.totalPages
          })
          .catch((err) => {
            console.error(err)
          })
      this.isLoading = false
    },
    createApi() {
      Resource.post
          .create({ data: this.inputValues })
          .then(() => {
            this.searchApi()
            alert('등록됐습니다.')
          })
          .catch((err) => {
            console.error(err)
            alert('Error!')
          })

      this.toggleIsOpen()
    },
    readApi(id) {
      Resource.post
          .read({ id })
          .then((res) => {
            const item = res.data
            this.activeId = item.id
            this.inputValues.title = item.title
            this.inputValues.name = item.name
            this.toggleIsOpen()
          })
          .catch((err) => {
            console.error(err)
            alert('Error!')
          })
    },
    updateApi(id) {
      Resource.post
          .update({ id, data: this.inputValues })
          .then(() => {
            alert('수정됐습니다.')
          })
          .catch((err) => {
            console.error(err)
            alert('Error!')
          })

      this.toggleIsOpen()
    },
    deleteApi(id) {
      Resource.post
          .delete({ id })
          .then(() => {
            alert('삭제됐습니다.')
          })
          .catch((err) => {
            console.error(err)
            alert('Error!')
          })

      this.toggleIsOpen()
    },
  },
}
</script>

<style></style>

import Vue from 'vue'

const baseUrl = process.env.VUE_APP_SERVER_URL
const headers = {
	// Authorization: 'Bearer ...' // token
}

export default {
	posts: {
		// 등록
		create(data) {
			return Vue.axios({
				headers,
				url: `${baseUrl}/posts`,
				method: 'POST',
				data: data.data,
			})
		},
		// 단일 조회
		read(data) {
			return Vue.axios({
				headers,
				url: `${baseUrl}/posts/${data.id}`,
				method: 'GET',
			})
		},
		// 수정
		update(data) {
			return Vue.axios({
				headers,
				url: `${baseUrl}/posts/${data.id}`,
				method: 'PUT',
				data: data.data,
			})
		},
		// 삭제
		delete(data) {
			return Vue.axios({
				headers,
				url: `${baseUrl}/posts/${data.id}`,
				method: 'DELETE',
			})
		},
		// 전체 조회
		search(data) {
			return Vue.axios({
				headers,
				url: `${baseUrl}/posts`,
				method: 'GET',
				params: data.params
			})
		},
	},
}

<template>
	<v-app>
		<v-app-bar id="app">
      <v-spacer/>
			<v-btn text to='/app/table' outlined>Table</v-btn>
		</v-app-bar>

		<v-main>
			<v-container class='py-12' fluid>
				<router-view/>
			</v-container>
		</v-main>
	</v-app>
</template>

<script>
export default {
	name: 'App',
}
</script>

<style></style>

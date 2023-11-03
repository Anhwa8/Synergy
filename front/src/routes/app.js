export default [
    {
        path: '/',
        name: '/',
        redirect: '/app',
        component: () => import('@/main.vue'),
        children: [
            {
                path: '/app',
                name: '/app',
                component: () => import('@/app/App.vue'),
                children: [
                    {
                        path: '/app/table',
                        name: '/app/table',
                        component: () => import('@/app/table/Table.vue')
                    },
                ],
            },
        ],
    },
]

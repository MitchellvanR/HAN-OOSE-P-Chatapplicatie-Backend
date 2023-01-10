import {createLocalVue, shallowMount} from '@vue/test-utils';
import menu from '@/views/menu';
import sinon from 'sinon';

const localVue = createLocalVue();

describe('menu.vue', () => {
    let wrapper;
    const listener = sinon.spy();

    it('Is called UserMenu', () => {
        let createChat = jest.fn();
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });

        expect(wrapper.name()).toEqual('UserMenu');
    });

    it('check if setUserId is called after click button user 1', async () => {
        let createChat = jest.fn();
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });

        wrapper.find('#user1').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 2', async () => {
        let createChat = jest.fn();
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });

        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 3', async () => {
        let createChat = jest.fn();
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });

        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button administrator', async () => {
        let createChat = jest.fn();
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });

        wrapper.find('#administrator').trigger('click');
        expect(listener.called);
    });
});
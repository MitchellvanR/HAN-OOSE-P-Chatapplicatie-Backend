import {createLocalVue, shallowMount} from '@vue/test-utils';
import menu from '@/views/menu';
import sinon from 'sinon';

const localVue = createLocalVue();

describe('menu.vue', () => {
    let wrapper;
    const listener = sinon.spy();
    let createChat = jest.fn();

    function setWrapper() {
        wrapper = shallowMount(menu, {
            methods: {
                createChat
            },
            localVue
        });
    }

    it('Is called UserMenu', () => {
        setWrapper();
        expect(wrapper.name()).toEqual('UserMenu');
    });

    it('check if setUserId is called after click button user 1', () => {
        setWrapper();
        wrapper.find('#user1').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 2', () => {
        setWrapper();
        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button user 3', () => {
        setWrapper();
        wrapper.find('#user2').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button administrator', () => {
        setWrapper();
        wrapper.find('#administrator').trigger('click');
        expect(listener.called);
    });

    it('calls createChat on mount ',() => {
        setWrapper();
        expect(createChat).toBeCalled();
    });
});
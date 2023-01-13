import {createLocalVue, shallowMount} from '@vue/test-utils';
import menu from '@/views/menu';
import sinon from 'sinon';

const localVue = createLocalVue();

describe('menu.vue', () => {
    let wrapper;
    const listener = sinon.spy();

    function setWrapper() {
        wrapper = shallowMount(menu, {
            localVue
        });
    }

    it('Is called UserMenu', () => {
        setWrapper();
        expect(wrapper.name()).toEqual('UserMenu');
    });

    it('check if setUserId is called after click button user', () => {
        setWrapper();
        wrapper.find('#user1').trigger('click');
        expect(listener.called);
    });

    it('check if setUserId is called after click button administrator', () => {
        setWrapper();
        wrapper.find('#administrator').trigger('click');
        expect(listener.called);
    });
});
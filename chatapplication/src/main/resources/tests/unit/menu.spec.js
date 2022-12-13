import { mount } from 'vue-test-utils';
import { expect } from "chai";
import menu from '@/views/menu'

describe('menu.vue', () => {
    it('Is called UserMenu', () => {
        const wrapper = mount(menu);

        // Assert result
        expect(wrapper.name()).to.equal('UserMenu');
    })
})
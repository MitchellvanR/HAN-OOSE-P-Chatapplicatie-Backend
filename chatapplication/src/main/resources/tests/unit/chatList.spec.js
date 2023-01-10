import {createLocalVue, shallowMount} from "@vue/test-utils";
import chatList from "@/views/chatList";
import sinon from "sinon";

const localVue = createLocalVue();

describe('chatList.vue', () => {
    let wrapper;
    const listener = sinon.spy();
    let addToItems = jest.fn();

    function setWrapper() {
        wrapper = shallowMount(chatList, {
            methods: {
                addToItems
            },
            localVue
        });
    }

    it('is called openChatList', () => {
        setWrapper();
        expect(wrapper.name()).toEqual('openChatList');
    });

    it('calls addToList on mount',  () => {
        setWrapper();
        expect(addToItems).toBeCalled();
    });

    it('check if setChatId is called after click button', () => {
        setWrapper();
        wrapper.find('.btn').trigger('click');
        expect(listener.called);
    });
});
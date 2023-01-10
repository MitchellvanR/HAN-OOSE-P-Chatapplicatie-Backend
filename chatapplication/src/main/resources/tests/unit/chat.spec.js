import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import chat from '@/views/chat';
import sinon from "sinon";

const localVue = createLocalVue()

describe('chat.vue', () => {
    let wrapper;
    const listener = sinon.spy();
    let getChatLog = jest.fn();
    let savePublicKey = jest.fn();
    let delay = jest.fn();
    let addUserToCurrentChat = jest.fn();

    function setWrapper() {
        wrapper = shallowMount(chat, {
            methods: {
                getChatLog,
                savePublicKey,
                delay,
                addUserToCurrentChat
            },
            localVue
        })
    };

    it('is called OpenChat', () => {
        setWrapper();
        expect(wrapper.name()).toEqual('OpenChat');
    });

    it('calls getChatLog on mount', () => {
        setWrapper();
        expect(getChatLog).toBeCalled();
    });

    it('calls savePublicKey on mount', () => {
        setWrapper();
        expect(savePublicKey).toBeCalled();
    });

    it('calls delay on mount', () => {
        setWrapper();
        expect(delay).toBeCalled();
    });

    it('calls addUserToCurrentChat on mount', () => {
        setWrapper();
        expect(addUserToCurrentChat).toBeCalled();
    });

    it('check if openForm is called after click button openForm', () => {
        setWrapper();
        wrapper.find('#openForm').trigger('click');
        expect(listener.called);
    });

    it('check if closeForm is called after click button closeForm', () => {
        setWrapper();
        wrapper.find('#closeForm').trigger('click');
        expect(listener.called);
    });
})
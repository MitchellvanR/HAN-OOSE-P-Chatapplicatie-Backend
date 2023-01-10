import {createLocalVue, mount, shallowMount} from '@vue/test-utils';
import chat from '@/views/chat';

const localVue = createLocalVue()

describe('chat.vue', () => {
    let getChatLog = jest.fn();
    let savePublicKey = jest.fn();
    let delay = jest.fn();
    let addUserToCurrentChat = jest.fn();

    let wrapper = shallowMount(chat, {
        methods: {
            getChatLog,
            savePublicKey,
            delay,
            addUserToCurrentChat
        },
        localVue
    })

    it('is called OpenChat', () => {
        expect(wrapper.name()).toEqual('OpenChat');
    })

    it('calls getChatLog on mount', () => {
        expect(getChatLog).toBeCalled();
    })

    it('calls savePublicKey on mount', () => {
        expect(savePublicKey).toBeCalled();
    })

    it('calls delay on mount', () => {
        expect(delay).toBeCalled();
    })

    it('calls addUserToCurrentChat on mount', () => {
        expect(addUserToCurrentChat).toBeCalled();
    })
})
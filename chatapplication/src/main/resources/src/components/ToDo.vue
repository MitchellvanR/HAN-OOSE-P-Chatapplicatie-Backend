<!-- voorbeeld voor Jesse voor de unit tests -->
<template>
  <div
      class="list-group-item list-group-item-action"
      :class="{'disabled': isFinished}"
      @click.stop="toggle">

    <div class="d-flex align-items-center justify-content-between">
      <div>
        <slot>{{ content }}</slot>
      </div>
      <button
          class="btn btn-danger"
          @click.stop="remove"
      >Remove</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ToDo',
  props: {
    content: {
      required: false,
      type: String
    },

    isFinished: {
      required: false,
      type: Boolean,
    },
  },
  methods: {
    remove() {
      this.$emit('remove');
    },
    toggle() {
      this.$emit('toggle');
    },
  }
};
</script>
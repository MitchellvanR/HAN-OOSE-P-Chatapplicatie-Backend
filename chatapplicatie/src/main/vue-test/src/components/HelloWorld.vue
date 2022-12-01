<template>
  <div class="hello">
    <div class="holder">

      <form @submit.prevent="addSkill">
        <input type="text" placeholder="Enter a skill you have.." v-model="skill" name="skill">
      </form>

      <ul>
        <li v-for="(data, index) in skills" :key='index'>{{ data.skill }}</li>
      </ul>

      <p>These are the skills that you process.</p>
    </div>
  </div>
</template>

<script>
import {required} from "vuelidate/lib/validators";

export default {
  name: 'HelloWorld',
  data() {
    return {
      skill: '',
      skills: [
        {"skill": "Vue.js"},
        {"skill": "Frontend Developer"}
      ],
    }
  },
  validations: {
    form: {
      skill: { required },
    }
  },
  methods: {
    addSkill() {
      this.skills.push({skill: this.skill})
      this.skill = '';
      console.log(this.checked);
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .holder {
    background: #ffffff;
  }

  ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
  }

  ul li {
    padding: 20px;
    font-size: 1.3em;
    background-color: #E0EDF4;
    border-left: 5px solid #3EB3F6;
    margin-bottom: 2px;
    color: #3E5252;
  }

  p {
    text-align: center;
    padding: 30px 0;
    color: gray;
  }

  .container {
    box-shadow: 0px 0px 40px lightgray;
  }

  input {
    width: calc(100% - 40px);
    border: 0;
    padding: 20px;
    font-size: 1.3em;
    background-color: #323333;
    color: #687F7F;
  }
</style>

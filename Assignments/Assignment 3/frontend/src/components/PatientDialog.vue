<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add patient" : "Edit patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field v-model="patient.idc" label="IDC" />
          <v-text-field v-model="patient.pnc" label="PNC" />
          <v-text-field v-model="patient.address" label="address" />
          <v-text-field v-model="patient.date_of_birth" label="Date of Birth" />
          
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
           
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patients
          .create({
            name: this.patient.name,
            idc: this.patient.idc,
            pnc: this.patient.pnc,
            address: this.patient.address,
            date_of_birth: this.patient.date_of_birth,

          })
          .then(() => this.$emit("refresh"));
      } else {
        api.patients
          .edit({
            id: this.patient.id,
            name: this.patient.name,
            idc: this.patient.idc,
            pnc: this.patient.pnc,
            address: this.patient.address,
            date_of_birth: this.patient.date_of_birth,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
};
</script>

<style scoped></style>

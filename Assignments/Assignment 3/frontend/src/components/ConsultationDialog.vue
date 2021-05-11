<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-bind:readonly="isDoctor"
                  v-model="consultation.patient"
                  label="Patient"
                  solo
                  :items="patients"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-bind:readonly="isDoctor"
                  v-model="consultation.doctor"
                  label="Doctor"
                  solo
                  :items="doctors"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
          <v-text-field v-model="consultation.description" label="Description" />
              </v-col>
            </v-row>
              <v-row>
              <v-col cols="24" sm="12" md="8">
           <v-text-field v-bind:readonly="isDoctor" v-model="consultation.data" label="Data yyyy-MM-dd" />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="hour"
                  v-bind:readonly="isDoctor"
                  label="Hour"
                  solo
                  :items="[8,9,10,11,12,13,14,15,16,17]"
                ></v-autocomplete>
              </v-col>
            </v-row>
             </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn  @click="remove" >
           Delete
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultationDialog",
  data() {
    return {
      hour: null,
    };
  },
  props: {
    consultation: Object,
    opened: Boolean,
    patients: Array,
    doctors: Array,
    isDoctor: Boolean,
  },
  methods: {
    remove(){
      api.consultations.remove(this.consultation.id)
      .then(() => this.$emit("refresh")); 
    },
    persist() {
      var dates=new String(this.consultation.data+"T"+this.hour+ ":00")
      if (this.isNew) {
        api.consultations
          .create({
            patient: this.consultation.patient,
            doctor: this.consultation.doctor,
            description: this.consultation.description,
            data: dates,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.consultations
          .edit({
            id: this.consultation.id,
            patient: this.consultation.patient,
            doctor: this.consultation.doctor,
            description: this.consultation.description,
            data: dates,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
  },
};
</script>

<style scoped></style>

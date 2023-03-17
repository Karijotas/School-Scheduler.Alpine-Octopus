import React from "react";
import {
  Component
} from "semantic-ui-react";
// import DatePicker from "react-datepicker";
// import dayjs from "dayjs";
// import { format } from 'date-fns'
import DumbComponent from "./DumbComponent";


export default class ContainerComponent extends Component {

    state = { name: "", startingDate: "", plannedTillDate: "", status: "" };
  
  
    fetchSchedules = async () => {
        fetch("/alpine-octopus/api/v1/schedule/")
        .then(res => this.setState({ data: res.data }));
    }

  
    render() {
      const { name, startingDate, plannedTillDate, status } = this.state;

      return (
        <div>
          <DumbComponent name={name} startingDate={startingDate} plannedTillDate={plannedTillDate} status={status} />
        </div>
      )

                }}
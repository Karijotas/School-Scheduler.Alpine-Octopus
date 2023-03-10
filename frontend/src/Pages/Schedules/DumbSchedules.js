import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input,
  Pagination,
  Segment,
  Table,
  TextArea,
  Component
} from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
// import DatePicker from "react-datepicker";
// import dayjs from "dayjs";
// import { format } from 'date-fns'
import { StatusButton } from "./StatusButton";
import DumbComponent from "./DumbComponent";


export default class ContainerComponent extends Component {

    state = { name: "", startingDate: "", plannedTillDate: "", status: "" };
  
  
    fetchSchedules = async () => {
        fetch("/api/v1/schedule/")
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
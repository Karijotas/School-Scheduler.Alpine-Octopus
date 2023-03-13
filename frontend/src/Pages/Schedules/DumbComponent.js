const DumbComponent = ({ name, startingDate, plannedTillDate,status }) => {
    return (
      <div>
        My complete information:
        <h3>{name}</h3>
        <h3>{startingDate}</h3>
        <h3>{plannedTillDate}</h3>
        <h3>{status}</h3>
      </div>
    )
  }
  
  export default DumbComponent;
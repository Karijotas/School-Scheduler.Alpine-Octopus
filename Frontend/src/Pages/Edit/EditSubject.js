import {useParams} from "react-router-dom";
import { useState, useEffect } from "react";

export function EditSubject(){

    const params = useParams();
    const [error, setError] = useState();

    const [subject, setSubject] = useState({
        name: "",
        description: "",
    });

    useEffect(() => {
        fetch('/api/v1/subjects/' + params.id)
        .then(response => response.json())
        .then(setSubject);
}, []);


const updateSubject = () => {
    fetch("/api/v1/subjects/" + params.id, {
        method: "PATCH",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(subject)
    }).then(result => {
        if (!result.ok) {
            setError('Update failed');
        } else {
            setError();
        }
    });
};


    const updateProperty = (property, event) => {
        setSubject({
            ...subject,
            [property]: event.target.value
        });
    };


    return(
        <div></div>
    )
    // <Container style={{ width: "25rem" }}>
    //   <Card>
    //     <Card.Body>
    //         <h3>Update Holiday</h3>

    //         <fieldset>
    //             <legend>{params.id}</legend>

    //             {error && (<div className='error'>{error}</div>)}

    //             {/* <label>Name</label>
    //             <input value={holiday.name} onChange={(e) => updateProperty('name', e)}></input>

    //             <label>Type</label>
    //             <select value={holiday.type} onChange={(e) => updateProperty('type', e)}>
    //             {Object.entries(HOLIDAY_TYPES)
    //                 .map(([key, value]) => <option key={key} value={key}>{value}</option>)}
    //             </select>

    //             <label>Destination</label>
    //             <input value={holiday.destination} onChange={(e) => updateProperty('destination', e)}></input>

    //             <button onClick={updateHoliday}>Update</button> */}
    //         </fieldset>

    //         <Form>

    //   <Form.Group className="mb-3" controlId="formName">
    //     <Form.Label htmlFor="name">Name</Form.Label>
    //     <Form.Control
    //             value={holiday.name} onChange={(e) => updateProperty('name', e)}
    //           />
    //   </Form.Group>

    //   <Form.Group className="mb-3" controlId="formType">
    //     <Form.Label htmlFor="type">Holiday type</Form.Label>
    //     <Form.Select value={holiday.type} onChange={(e) => updateProperty('type', e)}>
    //     {Object.entries(HOLIDAY_TYPES)
    //                 .map(([key, value]) => <option key={key} value={key}>{value}</option>)}
    //           </Form.Select>
    //   </Form.Group>

    //   <Form.Group className="mb-3" controlId="formDestination">
    //     <Form.Label htmlFor="destination">Destination</Form.Label>
    //     <Form.Control value={holiday.destination} onChange={(e) => updateProperty('destination', e)}/>
    //   </Form.Group>
      
    //   <Form.Group className="mb-3" controlId="formType">
    //   <Button variant="outline-dark" onClick={updateHoliday}>
    //             Update
    //           </Button>
    //   </Form.Group>

    // </Form>

    //     </Card.Body>
    //   </Card>
    // </Container>
    // )
}

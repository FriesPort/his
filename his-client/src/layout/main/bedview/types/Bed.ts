export interface Bed {
  bedId: number;
  bedNumber: string;
  roomNumber: string;
  bedStatus: string;
  roomType: string;
  roomGender: string;
  bedType: string,
  bedCount: number,
  patientId: number | null,
  patientName: string | null,
  patientAge: number | null,
  patientGender: string | null,
  telephoneNumber: string | null,
  admissionType: string | null,
  admissionTime: string | null,
  admissionNumber: string | null,
  bookType: string | null,
  doctor: string | null,
  illness: string | null,
  isEmergency: number | null,
  isAcute: number | null,
  isVip: number | null
}



export interface Room {
  roomId: number,
  roomNumber: string,
  bedCount: number,
  roomGender: string
}
export interface Ward {
  wardId: number;
  wardName: string
}

export interface Office {
  officeId: number,
  officeName: string,
  wards: Ward[]
}

export interface Campus {
  campusId: number;
  campusName: string;
  offices: Office[]
}
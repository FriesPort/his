export interface Patient {
  patientId: number,
  name: string,
  age: number,
  gender: string,
  phone: string,
  bookType: string | null,
  status: string,
  roomNumberRequirement: number | null,
  roomGenderRequirement: string | null,
  roomTypeRequirement: string | null,
  admissionType: string,
  admissionTime: string | null,
  createTime: string,
  admissionNumber: string | null,
  waitDay: number | null,
  doctor: string,
  illness: string,
  isEmergency: number,
  isAcute: number,
  isVip: number
}
export interface Bed {

  bedId: number;
  roomNumber: string;
  bedNumber: string;
  bedType: string;
  roomType: string;
  roomGender: string;
  bedCount: number;
  patient: Patient | null

}

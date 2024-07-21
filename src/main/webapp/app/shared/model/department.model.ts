import dayjs from 'dayjs';
import { ICreateuser } from 'app/shared/model/createuser.model';
import { dept_st } from 'app/shared/model/enumerations/dept-st.model';

export interface IDepartment {
  id?: string;
  dept_name?: string;
  dept_sname?: string;
  dept_status?: keyof typeof dept_st;
  date?: dayjs.Dayjs | null;
  createuser?: ICreateuser | null;
}

export const defaultValue: Readonly<IDepartment> = {};
